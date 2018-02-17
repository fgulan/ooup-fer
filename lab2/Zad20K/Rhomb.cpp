//
//  Rhomb.cpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include "Rhomb.hpp"
#include <iostream>

Rhomb::Rhomb(Point const& center, double side, double angle) : Shape(center), side_(side), angle_(angle) {}

void Rhomb::draw() {
    std::cout << "in drawRhomb\n";
}