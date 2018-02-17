//
//  Square.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Square_hpp
#define Square_hpp

#include "Shape.hpp"

class Square : public Shape {
    double side_;
public:
    Square() : Shape() {};
    Square(Point const& center, double side);
    virtual void draw();
    virtual ~Square() {};
};

#endif /* Square_hpp */
