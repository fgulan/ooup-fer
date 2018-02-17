//
//  Shape.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Shape_hpp
#define Shape_hpp

#include "Point.hpp"

class Shape {
    Point center_;
public:
    Shape() {};
    Shape(Point const& center);
    virtual void draw() = 0;
    virtual void translate(int dx, int dy);
    virtual ~Shape() {};
};
#endif /* Shape_hpp */
