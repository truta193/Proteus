//
//  ScheduleModel.swift
//  proteus-mac
//
//  Created by Andrei Truta on 22.02.2025.
//

import Foundation
import FirebaseFirestore

struct Schedule: Identifiable, Hashable, Codable {
    @DocumentID var id: String?
    
    var title: String
    var events: [Event]
    
    init (title: String, events: [Event] = []) {
        self.title = title
        self.events = events
    }
    
    static func == (lhs: Schedule, rhs: Schedule) -> Bool {
        lhs.id == rhs.id
    }
    
    var displayName: String {
        self.title
    }
    
    var iconName: String {
        "star"
    }
    
    static func example() -> Schedule {
        Schedule(title: "Example Schedule", events: [Event.example()])
    }
    
    static func examples() -> [Schedule] {
        [.example(), .example()]
    }
    
}
