//
//  proteus_macApp.swift
//  proteus-mac
//
//  Created by Andrei Truta on 22.02.2025.
//

import SwiftUI
import FirebaseAuth
import FirebaseCore

@main
struct proteus_macApp: App {
    @State var authViewModel: AuthViewModel
    @State var firestoreManager: FirestoreManager
    
    init() {
        FirebaseApp.configure()
        
        self.authViewModel = AuthViewModel()
        self.firestoreManager = FirestoreManager()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(authViewModel)
                .environment(firestoreManager)
        }
        
        Settings {
            
        }
    }
}
