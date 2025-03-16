//
//  TimetableView.swift
//  proteus-mac
//
//  Created by Andrei Truta on 23.02.2025.
//

import SwiftUI
struct TimetableView: View {
    let schedule: Schedule
    
    let days = [
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
        "Sunday",
    ]
    let hours = Array(7...20).map { String(format: "%02d:00", $0) }
    
    let hoursWidth: CGFloat = 30.0
    let headerHeight: CGFloat = 40.0
    let dayHeight: CGFloat = 60.0
    
    var body: some View {
        VStack(spacing: 0) {
            // Top row with days
            HStack(spacing: 0) {
                Rectangle()
                    .fill(.clear)
                    .frame(width: hoursWidth, height: headerHeight)
                    .overlay(
                        Rectangle().frame(
                            width: nil, height: 1, alignment: .top
                        ).foregroundColor(Color.gray.opacity(0.2)),
                        alignment: .bottom)
                
                ForEach(days, id: \.self) { day in
                    Text(day)
                        .frame(maxWidth: .infinity)
                        .frame(height: headerHeight)
                        .overlay(
                            Rectangle().frame(
                                width: nil, height: 1, alignment: .top
                            ).foregroundColor(Color.gray.opacity(0.2)),
                            alignment: .bottom)
                }
            }
            
            ScrollView(.vertical, showsIndicators: true) {
                ZStack(alignment: .topLeading) {
                    // Base grid
                    HStack(spacing: 0) {
                        //Hours (on the left)
                        VStack(spacing: 0) {
                            ForEach(hours, id: \.self) { hour in
                                Text(hour)
                                    .frame(width: hoursWidth, height: dayHeight)
                                    .offset(x: 0, y: -dayHeight / 4)
                                    .font(.footnote)
                            }
                        }
                        
                        //Day columns (1 top padding + hour intervals)
                        HStack(spacing: 0) {
                            ForEach(days, id: \.self) { day in
                                VStack(spacing: 0) {
                                    Rectangle()
                                        .fill(.clear)
                                        .frame(height: dayHeight / 2)
                                        .overlay(
                                            Rectangle().frame(
                                                width: 1, height: nil,
                                                alignment: .leading
                                            ).foregroundColor(
                                                .gray.opacity(0.2)),
                                            alignment: .trailing)
                                    
                                    ForEach(hours, id: \.self) { _ in
                                        Rectangle()
                                            .fill(.clear)
                                            .frame(height: dayHeight)
                                            .overlay(
                                                Rectangle().frame(
                                                    width: 1, height: nil,
                                                    alignment: .leading
                                                ).foregroundColor(
                                                    .gray.opacity(0.2)),
                                                alignment: .trailing
                                            )
                                            .overlay(
                                                Rectangle().frame(
                                                    width: nil, height: 1,
                                                    alignment: .top
                                                ).foregroundColor(
                                                    Color.gray.opacity(0.2)),
                                                alignment: .top)
                                    }
                                }
                                .frame(maxWidth: .infinity)
                            }
                        }
                    }
                    
                    // Events overlay with floating-point positioning
                    GeometryReader { geometry in
                        ForEach(self.schedule.events) { event in
                            if let dayIndex = days.firstIndex(of: event.day) {
                                let width =
                                max(1, (geometry.size.width - hoursWidth)
                                    / CGFloat(days.count))
                                let xOffset =
                                hoursWidth + (CGFloat(dayIndex) * width)
                                
                                // Parse the first hour string to get the base hour
                                let baseHour = Double(hours.first!.prefix(2))!
                                
                                // Calculate offset
                                let hourOffset = event.startTime - baseHour
                                let yOffset =
                                dayHeight / 2
                                + (CGFloat(hourOffset) * dayHeight)
                                
                                EventView(
                                    event: event,
                                    width: width,
                                    height: dayHeight
                                )
                                .padding(2)
                                .offset(x: xOffset, y: yOffset)
                            }
                        }
                    }
                }
            }
        }
        .frame(minWidth: 500)
    }
}

#Preview {
    TimetableView(schedule: Schedule.example())
}
