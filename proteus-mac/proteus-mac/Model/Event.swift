//
//  Task.swift
//  proteus-mac
//
//  Created by Andrei Truta on 22.02.2025.
//

import Foundation
import SwiftUI
import FirebaseFirestore

struct Event: Identifiable, Hashable, Codable {
    @DocumentID var id: String?
    
    var title: String
    var day: String
    var startTime: Double
    var durationHours: Double
    //var color: Color
    
    init(title: String, day: String, startTime: Double, durationHours: Double, color: Color) {
        self.title = title
        self.day = day
        self.startTime = startTime
        self.durationHours = durationHours
        //self.color = color
    }
    
    static func example() -> Event {
        Event(title: "Example task", day: "Monday", startTime: 10, durationHours: 1, color: .blue)
    }
    
    static func examples() -> [Event] {
        [.example(), .example()]
    }
}
