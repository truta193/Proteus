//
//  TaskView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 26.02.2025.
//

import SwiftUI

struct EventView: View {
    let event: Event
    let width: CGFloat
    let height: CGFloat
    
    var body: some View {
        ZStack(alignment: .topLeading) {
            // Background rectangle
            RoundedRectangle(cornerRadius: 8)
                .fill(.green.opacity(0.3))
                .frame(
                    width: max(1, width - 4),
                    height: max(1, height * event.durationHours - 4)
                )
            
            // Left vertical line
            RoundedRectangle(cornerRadius: 8)
                .fill(.green)
                .frame(
                    width: 4,
                    height: max(1, height * event.durationHours - 16)
                )
                .padding(.leading, 4)
                .padding(.top, 6)
            
            Text(event.title)
                .foregroundStyle(.green)
                .font(.subheadline.bold())
                .frame(
                    width: max(1, width - 24),
                    alignment: .leading
                )
                .padding(.top, 4)
                .padding(.leading, 12)
        }
    }
}

#Preview {
    EventView(
        event: Event.example(),
        width: 100,
        height: 120
    )
}
