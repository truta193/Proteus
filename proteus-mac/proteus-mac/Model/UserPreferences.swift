//
//  UserDetails.swift
//  proteus-mac
//
//  Created by Andrei Truta on 03.03.2025.
//

import Foundation

struct UserPreferences: Identifiable, Codable, Equatable {
    var id: String
    var activeSchedule: String?
}
