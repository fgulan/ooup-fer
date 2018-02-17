//
//  Shape.cpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include "Shape.hpp"

Shape::Shape(Point const& center) : center_{center} {}

void Shape::translate(int dx, int dy) {
    center_.translate(dx, dy);
}
