//
//  SidebarView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 23.02.2025.
//

import SwiftUI

struct SidebarView: View {
    @Environment(AuthViewModel.self) var authViewModel
    @Environment(FirestoreManager.self) var firestoreManager
    
    private var activeScheduleId: String? {
        return firestoreManager.userPreferences?.activeSchedule
    }
    
    @State private var isShowingNewScheduleSheet = false
    @State private var selectedScheduleId: String?
    
    var body: some View {
        
        List(selection: $selectedScheduleId) {
            Section("Schedules") {
                ForEach(firestoreManager.userSchedules) { schedule in
                    if activeScheduleId == schedule.id {
                        Label(schedule.title, systemImage: "tray.fill")
                            .tag(schedule.id)
                    } else {
                        Label(schedule.title, systemImage: "tray")
                            .tag(schedule.id)
                    }
                }
            }
        }
        .onChange(of: activeScheduleId) { oldValue, newValue in
            selectedScheduleId = newValue
        }
        .onChange(of: selectedScheduleId) { oldValue, newValue in
            if let scheduleId = newValue, scheduleId != activeScheduleId, let user = authViewModel.user {
                Task {
                    try? await firestoreManager.updateActiveSchedule(
                        user: user,
                        scheduleId: scheduleId
                    )
                }
            }
        }
        .onAppear {
            selectedScheduleId = activeScheduleId
        }
        .safeAreaInset(edge: .bottom) {
            HStack {
                Button(action: {
                    isShowingNewScheduleSheet = true
                    
                }) {
                    HStack() {
                        Image(systemName: "plus.circle")
                        Text("New Schedule")
                            .lineLimit(1)
                    }
                    
                }
                .buttonStyle(.borderless)
                .foregroundStyle(Color.accentColor)
                
                Spacer()
            }
            .padding()
        }
        .sheet(isPresented: $isShowingNewScheduleSheet) {
            EditScheduleView(isShowingSheet: $isShowingNewScheduleSheet)
                .presentationDetents([.medium, .large])
                .presentationBackgroundInteraction(.automatic)
                .presentationBackground(.regularMaterial)
                .padding()
        }
    }
}

#Preview {
    SidebarView()
        .listStyle(.sidebar)
        .environment(AuthViewModel())
        .environment(FirestoreManager())
}
