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
    var userId: String
    
    init (title: String, events: [Event] = [], userId: String) {
        self.title = title
        self.events = events
        self.userId = userId
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
        Schedule(title: "Example Schedule", events: [Event.example()], userId: "Ou8orPL8VtSuT4PnCiYPWPcb8RG2")
    }
    
    static func examples() -> [Schedule] {
        [.example(), .example()]
    }
    
}
