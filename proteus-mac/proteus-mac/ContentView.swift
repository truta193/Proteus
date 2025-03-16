//
//  ContentView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 22.02.2025.
//

import SwiftUI

struct ContentView: View {
    @Environment(AuthViewModel.self) var authViewModel
    @Environment(FirestoreManager.self) var firestoreManager
    
    private var activeScheduleId: String? {
        if let preferredId = firestoreManager.userPreferences?.activeSchedule,
           firestoreManager.userSchedules.contains(where: { $0.id == preferredId }) {
            
            return preferredId
        }
        
        return nil
    }
    
    
    var body: some View {
        AuthenticatedView {
            NavigationSplitView {
                SidebarView()
                    .environment(firestoreManager)
                    .environment(authViewModel)
            } detail: {
                if let selectedId = activeScheduleId,
                   let schedule = firestoreManager.userSchedules.first(where: { $0.id == selectedId }) {
                    TimetableView(schedule: schedule)
                } else {
                    ContentUnavailableView {
                        Label("No schedule selected", systemImage: "tray.fill")
                    } description: {
                        Text("Events appear here once you select a schedule.")
                    }
                }
            }
        }
        .environment(authViewModel)
        .task {
            if let user = authViewModel.user {
                firestoreManager.listenToUserSchedules(user: user)
                firestoreManager.listenToUserPreferences(user: user)
            }
        }
        .onDisappear() {
            firestoreManager.stopListeningToSchedules()
            firestoreManager.stopListeningToUserPreferences()
        }
    }
}

#Preview {
    ContentView()
        .environment(AuthViewModel())
        .environment(FirestoreManager())
        .frame(width: 600)
}
