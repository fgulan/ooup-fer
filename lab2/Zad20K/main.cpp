//
//  main.cpp
//  Zad20K
//
//  Created by Filip Gulan on 15/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include <iostream>
#include <vector>

#include "Shape.hpp"
#include "Circle.hpp"
#include "Square.hpp"
#include "Rhomb.hpp"

void drawShapes(std::vector<Shape *> shapes) {
    for (auto &shape: shapes) shape->draw();
}

void moveShapes(std::vector<Shape *> shapes, int x, int y) {
    for (auto &shape: shapes)
        shape->translate(x, y);
}

int main() {
    using namespace std;
    
    vector<Shape *> shapes;
    
    shapes.push_back(new Circle());
    shapes.push_back(new Square());
    shapes.push_back(new Square());
    shapes.push_back(new Circle());
    shapes.push_back(new Rhomb());
    
    drawShapes(shapes);
    
    moveShapes(shapes, 3, 4);
    
    return 0;
}