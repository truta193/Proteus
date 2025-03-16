//
//  Firestore.swift
//  proteus-mac
//
//  Created by Andrei Truta on 03.03.2025.
//

import Observation
import Foundation
import FirebaseFirestore
import FirebaseAuth

@Observable
class FirestoreManager {
    private var database = Firestore.firestore()
    private var useSchedulesListener: ListenerRegistration?
    private var userPreferencesListener: ListenerRegistration?
    
    var userSchedules: [Schedule] = []
    var userPreferences: UserPreferences?
    
    
    func getUserPreferences(user: User) async throws {
        do {
            let docSnapshot = try await database.collection(Collections.users.rawValue).document(user.uid).getDocument()
            
            if docSnapshot.exists {
                if let data = docSnapshot.data() {
                    self.userPreferences = try Firestore.Decoder().decode(UserPreferences.self, from: data)
                } else {
                    self.userPreferences = nil
                }
            } else {
                self.userPreferences = nil
                print("No user preferences found for user ID: \(user.uid)")
            }
        } catch {
            print("Error fetching user preferences: \(error.localizedDescription)")
            throw error
        }
    }
    
    func updateActiveSchedule(user: User, scheduleId: String?) async throws {
        guard let scheduleId = scheduleId else {
            print("Cannot update active schedule with nil ID")
            return
        }
        
        do {
            let userDocRef = database.collection(Collections.users.rawValue).document(user.uid)
            let docSnapshot = try await userDocRef.getDocument()
            
            if docSnapshot.exists {
                try await userDocRef.updateData([
                    "activeSchedule": scheduleId
                ])
            } else {
                let newPreferences = UserPreferences(
                    id: user.uid,
                    activeSchedule: scheduleId
                )
                
                try userDocRef.setData(from: newPreferences)
            }
            
            if self.userPreferences != nil {
                self.userPreferences?.activeSchedule = scheduleId
            } else {
                self.userPreferences = UserPreferences(
                    id: user.uid,
                    activeSchedule: scheduleId
                )
            }
            
            print("Active schedule updated to \(scheduleId)")
        } catch {
            print("Error updating active schedule: \(error.localizedDescription)")
            throw error
        }
    }
    
    func createSchedule(schedule: Schedule) async throws {
        do {
            let newDocRef = database.collection(Collections.schedules.rawValue).document()
            try newDocRef.setData(from: schedule)
            
            print("Schedule '\(schedule.title)' created successfully with ID: \(newDocRef.documentID)")
        } catch {
            print("Error creating schedule: \(error.localizedDescription)")
            throw error
        }
    }
    
    func updateSchedule(schedule: Schedule) async throws {
        guard let scheduleId = schedule.id else {
            throw NSError(domain: "FirestoreError", code: 400, userInfo: [NSLocalizedDescriptionKey: "Cannot update schedule without an ID"])
        }
        
        do {
            let docRef = database.collection(Collections.schedules.rawValue).document(scheduleId)
            try docRef.setData(from: schedule, merge: true)
            
            print("Schedule '\(schedule.title)' updated successfully")
        } catch {
            print("Error updating schedule: \(error.localizedDescription)")
            throw error
        }
    }
    
    func getSchedule(id: String) async throws -> Schedule? {
        do {
            let docRef = database.collection(Collections.schedules.rawValue).document(id)
            let documentSnapshot = try await docRef.getDocument()
            
            if documentSnapshot.exists {
                let schedule = try documentSnapshot.data(as: Schedule.self)
                return schedule
            } else {
                print("No schedule found with ID: \(id)")
                return nil
            }
        } catch {
            print("Error fetching schedule: \(error.localizedDescription)")
            throw error
        }
    }
    
    func listenToUserPreferences(user: User) {
        userPreferencesListener = database.collection(Collections.users.rawValue)
            .document(user.uid)
            .addSnapshotListener { [weak self] documentSnapshot, error in
                guard let self = self else { return }
                
                if let error = error {
                    print("Error listening to user preferences: \(error.localizedDescription)")
                    return
                }
                
                guard let document = documentSnapshot, document.exists else {
                    print("User preferences document doesn't exist")
                    self.userPreferences = nil
                    return
                }
                
                do {
                    if let data = document.data() {
                        self.userPreferences = try Firestore.Decoder().decode(UserPreferences.self, from: data)
                        print("User preferences updated: active schedule ID - \(self.userPreferences?.activeSchedule ?? "none")")
                    }
                } catch {
                    print("Error decoding user preferences: \(error.localizedDescription)")
                }
            }
    }
    
    func stopListeningToUserPreferences() {
        userPreferencesListener?.remove()
        userPreferencesListener = nil
        print("Stopped listening to user preferences.")
    }
    
    func listenToUserSchedules(user: User) {
        useSchedulesListener = database.collection(Collections.schedules.rawValue)
            .whereField("userId", isEqualTo: user.uid)
            .addSnapshotListener { [weak self] snapshot, error in
                guard let self = self else { return }
                
                if let error = error {
                    print("Error listening to schedules: \(error.localizedDescription)")
                    return
                }
                
                guard let documents = snapshot?.documents else {
                    print("No schedules found.")
                    self.userSchedules = []
                    return
                }
                
                self.userSchedules = documents.compactMap { document in
                    do {
                        return try document.data(as: Schedule.self)
                    } catch {
                        print("Error decoding schedule document: \(error.localizedDescription)")
                        return nil
                    }
                }
                
                print("Successfully fetched \(self.userSchedules.count) schedules.")
            }
    }
    
    func stopListeningToSchedules() {
        useSchedulesListener?.remove()
        useSchedulesListener = nil
        print("Stopped listening to schedules.")
    }
}
