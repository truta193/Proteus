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
    
    @State private var selectedDay: String?
    @State private var selectedHour: Double?
    
    var body: some View {
        VStack(spacing: 0) {
            // Top row with days
            HStack(spacing: 0) {
                Rectangle()
                    .fill(.clear)
                    .frame(width: hoursWidth, height: headerHeight)
                    .overlay(
                        Rectangle().frame(width: nil, height: 1)
                            .foregroundColor(Color.gray.opacity(0.2)),
                        alignment: .bottom
                    )
                
                ForEach(days, id: \.self) { day in
                    Text(day)
                        .frame(maxWidth: .infinity)
                        .frame(height: headerHeight)
                        .overlay(
                            Rectangle().frame(width: nil, height: 1)
                                .foregroundColor(Color.gray.opacity(0.2)),
                            alignment: .bottom
                        )
                }
            }
            
            ScrollView(.vertical, showsIndicators: true) {
                TimeGrid(
                    days: days,
                    hours: hours,
                    hoursWidth: hoursWidth,
                    dayHeight: dayHeight,
                    events: schedule.events,
                    onCellTap: { day, hour in
                        selectedDay = day
                        selectedHour = hour
                        print("Selected: \(day) at \(String(format: "%.2f", hour))")
                    }
                )
                .frame(minHeight: CGFloat(hours.count) * dayHeight + dayHeight/2)
            }
        }
        .frame(minWidth: 500)
    }
}

struct TimeGrid: View {
    let days: [String]
    let hours: [String]
    let hoursWidth: CGFloat
    let dayHeight: CGFloat
    let events: [Event]
    let onCellTap: (String, Double) -> Void
    
    var body: some View {
        ZStack(alignment: .topLeading) {
            GridBase(
                days: days,
                hours: hours,
                hoursWidth: hoursWidth,
                dayHeight: dayHeight
            )
            
            // Cell detection overlay
            GeometryReader { geometry in
                Color.clear
                    .contentShape(Rectangle())
                    .gesture(
                        DragGesture(minimumDistance: 0)
                            .onEnded { value in
                                let location = value.location
                                let gridWidth = geometry.size.width - hoursWidth
                                let cellWidth = gridWidth / CGFloat(days.count)
                                let dayIndex = Int((location.x - hoursWidth) / cellWidth)
                                
                                let baseHour = Double(hours.first!.prefix(2))!
                                let hourValue = baseHour + Double((location.y - dayHeight/2) / dayHeight)
                                
                                if dayIndex >= 0 && dayIndex < days.count {
                                    onCellTap(days[dayIndex], hourValue)
                                }
                            }
                    )
            }
            
            EventsOverlay(
                days: days,
                hours: hours,
                events: events,
                hoursWidth: hoursWidth,
                dayHeight: dayHeight
            )
        }
    }
}

struct GridBase: View {
    let days: [String]
    let hours: [String]
    let hoursWidth: CGFloat
    let dayHeight: CGFloat
    
    var body: some View {
        HStack(spacing: 0) {
            // Hours column
            VStack(spacing: 0) {
                ForEach(hours, id: \.self) { hour in
                    Text(hour)
                        .frame(width: hoursWidth, height: dayHeight)
                        .offset(x: 0, y: -dayHeight / 4)
                        .font(.footnote)
                }
            }
            
            // Day columns
            HStack(spacing: 0) {
                ForEach(days, id: \.self) { day in
                    VStack(spacing: 0) {
                        Rectangle()
                            .fill(.clear)
                            .frame(height: dayHeight / 2)
                            .overlay(
                                Rectangle().frame(width: 1, height: nil)
                                    .foregroundColor(.gray.opacity(0.2)),
                                alignment: .trailing
                            )
                        
                        ForEach(hours, id: \.self) { _ in
                            Rectangle()
                                .fill(.clear)
                                .frame(height: dayHeight)
                                .overlay(
                                    Rectangle().frame(width: 1, height: nil)
                                        .foregroundColor(.gray.opacity(0.2)),
                                    alignment: .trailing
                                )
                                .overlay(
                                    Rectangle().frame(width: nil, height: 1)
                                        .foregroundColor(Color.gray.opacity(0.2)),
                                    alignment: .top
                                )
                        }
                    }
                    .frame(maxWidth: .infinity)
                }
            }
        }
    }
}

struct EventsOverlay: View {
    let days: [String]
    let hours: [String]
    let events: [Event]
    let hoursWidth: CGFloat
    let dayHeight: CGFloat
    
    var body: some View {
        GeometryReader { geometry in
            ForEach(events) { event in
                if let dayIndex = days.firstIndex(of: event.day) {
                    let width = (geometry.size.width - hoursWidth) / CGFloat(days.count)
                    let xOffset = hoursWidth + (CGFloat(dayIndex) * width)
                    
                    let baseHour = Double(hours.first!.prefix(2))!
                    let hourOffset = event.startTime - baseHour
                    let yOffset = dayHeight / 2 + (CGFloat(hourOffset) * dayHeight)
                    
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


#Preview {
    TimetableView(schedule: Schedule.example())
}
