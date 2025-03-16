//
//  LoginView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 01.03.2025.
//

import SwiftUI
import FirebaseAuth

struct SignInView: View {
    @Bindable var viewModel: AuthViewModel
    
    var body: some View {
        VStack() {
            Text("Proteus")
                .font(.largeTitle)
                .bold()
                .padding(.top)
            
            HStack {
                Image(systemName: "at")
                TextField("Email", text: $viewModel.email)
                    .textFieldStyle(.roundedBorder)
                    .textContentType(.emailAddress)
                    .autocorrectionDisabled()
            }
            .padding(.vertical, 6)
            .padding(.bottom, 4)
            
            HStack {
                Image(systemName: "lock")
                SecureField("Password", text: $viewModel.password)
                    .textFieldStyle(.roundedBorder)
                    .textContentType(.password)
            }
            .padding(.vertical, 6)
            .padding(.bottom, 8)
            
            if !viewModel.errorMessage.isEmpty {
                Text(viewModel.errorMessage)
                    .foregroundColor(.red)
                    .font(.callout)
            }
            
            Button {
                Task {
                    await viewModel.signIn()
                }
            } label: {
                HStack {
                    if viewModel.isAuthenticating {
                        ProgressView()
                            .scaleEffect(0.8)
                            .frame(width: 16, height: 16)
                            .padding(.trailing, 4)
                    }
                    
                    Text("Sign In")
                        .font(.headline)
                }
                .frame(maxWidth: .infinity)
                .frame(height: 38)
            }
            .buttonStyle(.borderedProminent)
            .controlSize(.large)
            .disabled(!viewModel.isValid || viewModel.isAuthenticating)
            
            Spacer()
        }
        .padding()
        .frame(maxWidth: 300, minHeight: 300)
    }
}

#Preview {
    let viewModel = AuthViewModel()
    
    SignInView(viewModel: viewModel)
}
