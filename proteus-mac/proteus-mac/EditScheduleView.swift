//
//  EditScheduleView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 05.03.2025.
//

import Foundation
import SwiftUI

struct EditScheduleView: View {
    @Environment(FirestoreManager.self) var firestoreManager
    @Environment(AuthViewModel.self) var authViewModel
    
    @State private var title: String
    
    @Binding var isShowingSheet: Bool
    private let scheduleId: String?
    
    init(schedule: Schedule, isShowingSheet: Binding<Bool>) {
        self._title = State(initialValue: schedule.title)
        
        self._isShowingSheet = isShowingSheet
        self.scheduleId = schedule.id
    }
    
    init(isShowingSheet: Binding<Bool>) {
        self._title = State(initialValue: "")
        self._isShowingSheet = isShowingSheet
        self.scheduleId = nil
    }
    
    var body: some View {
        NavigationStack {
            Form {
                TextField("Schedule Title", text: $title)
            }
            .navigationTitle(scheduleId == nil ? "New Schedule" : "Edit Schedule")
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Cancel") {
                        isShowingSheet = false
                    }
                }
                
                ToolbarItem(placement: .confirmationAction) {
                    Button(scheduleId == nil ? "Create" : "Save") {
                        saveSchedule()
                    }
                }
            }
        }
    }
    
    private func saveSchedule() {
        Task {
            do {
                if let scheduleId = scheduleId {
                    if let schedule = try await firestoreManager.getSchedule(id: scheduleId) {
                        var updatedSchedule = Schedule(
                            title: title,
                            events: [],
                            userId: schedule.userId
                        )
                        updatedSchedule.id = scheduleId
                        
                        try await firestoreManager.updateSchedule(schedule: updatedSchedule)
                    }
                    
                } else {
                    if let uid = authViewModel.user?.uid {
                        let newSchedule = Schedule(
                            title: title,
                            userId: uid
                        )
                        
                        try await firestoreManager.createSchedule(schedule: newSchedule)
                    }
                }
                
                await MainActor.run {
                    isShowingSheet = false
                }
            } catch {
                print("Error saving schedule: \(error)")
            }
        }
    }
}

#Preview {
    EditScheduleView(
        isShowingSheet: .constant(true)
    )
}

