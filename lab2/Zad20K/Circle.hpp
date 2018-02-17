//
//  Circle.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Circle_hpp
#define Circle_hpp

#include "Shape.hpp"

class Circle : public Shape {
    double radius_;
    
public:
    Circle() : Shape() {};
    Circle(Point const& center, double radius);
    virtual void draw();
    virtual ~Circle() {};
};

#endif /* Circle_hpp */
