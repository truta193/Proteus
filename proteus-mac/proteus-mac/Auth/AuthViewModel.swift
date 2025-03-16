//
//  AuthViewModel.swift
//  proteus-mac
//
//  Created by Andrei Truta on 01.03.2025.
//

import Foundation
import FirebaseAuth
import Observation
import Combine

enum AuthenticationState {
    case unauthenticated
    case authenticating
    case authenticated
}

@Observable
class AuthViewModel {
    var email = "" {
        didSet { updateValidation() }
    }
    var password = "" {
        didSet { updateValidation() }
        
    }
    var isValid = false
    var authenticationState: AuthenticationState = .unauthenticated
    var errorMessage = ""
    var user: User?
    
    var displayName: String {
        user?.email ?? ""
    }
    
    var isAuthenticated: Bool {
        authenticationState == .authenticated
    }
    
    var isAuthenticating: Bool {
        authenticationState == .authenticating
    }
    
    private var authStateHandler: AuthStateDidChangeListenerHandle?
    private var cancellables = Set<AnyCancellable>()
    
    init() {
        setupAuthStateHandler()
        setupValidation()
    }
    
    private func setupValidation() {
        updateValidation()
    }
    
    private func updateValidation() {
        isValid = !email.isEmpty && !password.isEmpty
    }
    
    private func setupAuthStateHandler() {
        authStateHandler = Auth.auth().addStateDidChangeListener { [weak self] _, user in
            guard let self else {return}
            self.user = user
            self.authenticationState = user == nil ? .unauthenticated : .authenticated
        }
    }
    
    func signIn() async {
        guard isValid else {
            errorMessage = "Email and password must not be empty."
            return
        }
        
        authenticationState = .authenticating
        errorMessage = ""
        
        do {
            try await Auth.auth().signIn(withEmail: email, password: password)
        } catch {
            errorMessage = error.localizedDescription
            authenticationState = .unauthenticated
        }
    }
    
    func signOut() {
        errorMessage = ""
        
        do {
            try Auth.auth().signOut()
        } catch {
            errorMessage = error.localizedDescription
        }
    }
    
    func reset() {
        email = ""
        password = ""
        errorMessage = ""
    }
    
    deinit {
        if let authStateHandler {
            Auth.auth().removeStateDidChangeListener(authStateHandler)
        }
        
        cancellables.removeAll()
    }
}
