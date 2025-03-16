//
//  AuthenticatedView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 01.03.2025.
//

import SwiftUI

extension AuthenticatedView where Unauthenticated == EmptyView {
    init(@ViewBuilder content: @escaping () -> Content) {
        self.unauthenticated = nil
        self.content = content
    }
}

struct AuthenticatedView<Content, Unauthenticated>: View where Content: View, Unauthenticated: View {
    //@State private var viewModel = AuthViewModel()
    @Environment(AuthViewModel.self) var viewModel
    var unauthenticated: Unauthenticated?
    @ViewBuilder var content: () -> Content
    
    public init(unauthenticated: Unauthenticated?, @ViewBuilder content: @escaping () -> Content) {
        self.unauthenticated = unauthenticated
        self.content = content
    }
    
    public init(@ViewBuilder unauthenticated: @escaping () -> Unauthenticated, @ViewBuilder content: @escaping () -> Content) {
        self.unauthenticated = unauthenticated()
        self.content = content
    }
    
    var body: some View {
        switch viewModel.authenticationState {
        case .unauthenticated, .authenticating:
            SignInView(viewModel: viewModel)
            
        case .authenticated:
            content()
                .toolbar {
                    ToolbarItem(placement: .navigation) {
                        Menu {
                            Button("Sign Out", role: .destructive) {
                                viewModel.signOut()
                            }
                        } label: {
                            Label(viewModel.displayName, systemImage: "person.circle")
                        }
                    }
                }
        }
    }
}

#Preview {
    AuthenticatedView {
        Text("You're signed in.")
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            .background(.gray)
    }.environment(AuthViewModel())
}

