//
//  EditScheduleView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 05.03.2025.
//

import Foundation
import SwiftUI

struct EditScheduleView: View {
    
    @Binding var firstName: String
    @Binding var lastName: String
    @State private var startTime = Date()
    @State private var endTime = Date()
    @State private var name: String = "Test"
    
    var body: some View {
        HStack {
            Form {
                DatePicker("Begin Time", selection: $startTime, displayedComponents: .hourAndMinute)
                DatePicker("End Time", selection: $endTime, displayedComponents: .hourAndMinute)
                TextField("Name", text: $name)
                TextField("First Name", text: $firstName)
                TextField("Last Name", text: $lastName)
            }
        }.padding()
    }
}

#Preview {
    EditScheduleView(
        firstName: .constant("John"),
        lastName: .constant("Doe")
    )
}

