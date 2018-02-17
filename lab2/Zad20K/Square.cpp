//
//  Square.cpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include "Square.hpp"

#include <iostream>

Square::Square(Point const& center, double side) : Shape(center), side_(side) {}

void Square::draw() {
    std::cout << "in drawSquare\n";
}