/*
 * created: 2004/09/13
 */
package jp.sourceforge.qrcode.geom;

import jp.sourceforge.qrcode.util.QRCodeUtility;

public class Line{
	int x1, y1, x2, y2;
	
	public Line() {
		x1 = y1 = x2 = y2 = 0;
	}
	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public Line(Point p1, Point p2) {
		x1 = p1.getX();
		y1 = p1.getY();
		x2 = p2.getX();
		y2 = p2.getY();
	}
	public Point getP1() {
		return new Point(x1, y1);
	}
	
	public Point getP2() {
		return new Point(x2, y2);
	}
	
	public void setLine(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public void setP1(Point p1) {
		x1 = p1.getX();
		y1 = p1.getY();
	}
	public void setP1(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
	}
	public void setP2(Point p2) {
		x2 = p2.getX();
		y2 = p2.getY();
	}
	public void setP2(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void translate(int dx, int dy) {
		this.x1 += dx;
		this.y1 += dy;
		this.x2 += dx;
		this.y2 += dy;
	}
	
	//check if two lines are neighboring. allow only 1 dot difference 
	public static boolean isNeighbor(Line line1, Line line2) {
		return ((Math.abs(line1.getP1().getX() - line2.getP1().getX()) < 2 &&
				Math.abs(line1.getP1().getY() - line2.getP1().getY()) < 2) &&
				(Math.abs(line1.getP2().getX() - line2.getP2().getX()) < 2 &&
				Math.abs(line1.getP2().getY() - line2.getP2().getY()) < 2));
	}


	
	public boolean isHorizontal() {
		return (y1 == y2);
	}
	
	public boolean isVertical() {
		return (x1 == x2);
	}
	
	public static boolean isCross(Line line1, Line line2) {
		if (line1.isHorizontal() && line2.isVertical()) {
			if (line1.getP1().getY() > line2.getP1().getY() &&
					line1.getP1().getY() < line2.getP2().getY() &&
					line2.getP1().getX() > line1.getP1().getX() &&
					line2.getP1().getX() < line1.getP2().getX())
				return true;
		} else if (line1.isVertical() && line2.isHorizontal()) {
			if (line1.getP1().getX() > line2.getP1().getX() &&
					line1.getP1().getX() < line2.getP2().getX() &&
					line2.getP1().getY() > line1.getP1().getY() &&
					line2.getP1().getY() < line1.getP2().getY())
				return true;
		}
		
		return false;
	}
	
	public Point getCenter() {
		int x = (x1 + x2) / 2;
		int y = (y1 + y2) / 2;
		return new Point(x , y);
	}
	public int getLength() {
		int x = Math.abs(x2 - x1);
		int y = Math.abs(y2 - y1);
		int r = QRCodeUtility.sqrt(x * x + y * y);
		return r;
	}
	public static Line getLongest(Line[] lines) {
		Line longestLine = new Line();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].getLength() > longestLine.getLength()) {
				longestLine = lines[i];
			}
		}
		return longestLine;
	}
	public String toString() {
    return "(" + x1 + "," + y1 + ")-(" + x2 + "," + y2 + ")";
	}
        
        public int getDeltaX() {
            return x2 - x1;
        }
        
        public int getDeltaY() {
            return y2 - y1;
        }
        
        public static int round(float f) {
            float remainder = f - (float)Math.floor(f);
            if(remainder > 0.5) {
                return (int)Math.ceil(f);
            }else {
                return (int)Math.floor(f);
            }
        }
        
        public Point getMidpoint(float f){
            
            return new Point(round(x1 + getDeltaX()* f),
                round(y1 + getDeltaY()* f));
        }
        
        public Point getIntersect(Line line) {
            int dx = getDeltaX();
            int dy = getDeltaY();
            double m1 = (double)getDeltaY() / (double)getDeltaX();
            double c1 = y1 - (m1*x1);
            
            double m2 = (double)line.getDeltaY() / (double)line.getDeltaX();
            double c2 = line.y1 - (m2 * line.x1);
            
            double x = (c2 - c1)/(m1 -m2);
            double y = m1 * x + c1;
            
            return new Point((int)round((float)x), (int)round((float)y));
        }
        
}
