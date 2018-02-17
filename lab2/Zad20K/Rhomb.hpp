//
//  Rhomb.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Rhomb_hpp
#define Rhomb_hpp

#include "Shape.hpp"

class Rhomb : public Shape {
    double side_;
    double angle_;
public:
    Rhomb() : Shape() {};
    Rhomb(Point const& center, double side, double angle);
    virtual void draw();
    virtual ~Rhomb() {};
};

#endif /* Rhomb_hpp */
