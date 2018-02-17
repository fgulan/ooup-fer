//
//  Circle.cpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include "Circle.hpp"

#include <iostream>

Circle::Circle(Point const& center, double radius) : Shape::Shape(center), radius_(radius) {}

void Circle::draw() {
    std::cout << "in drawCircle\n";
}
