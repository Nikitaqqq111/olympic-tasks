package training.one.d;

class Factors {

    final int a;
    final int b;
    final boolean particular;


    private Factors(int a, int b, boolean particular) {
        this.a = a;
        this.b = b;
        this.particular = particular;
    }

    static Factors calculateFactors(Point pointOne, Point pointTwo) {
        boolean particular = false;
        int a, b;
        if (pointOne.x - pointTwo.x == 0) {
            particular = true;
            b = -pointOne.x;
            a = 1;
        } else {
            b = (pointOne.x * pointTwo.y - pointTwo.x * pointOne.y) / (pointOne.x - pointTwo.x);
            a = (pointOne.y - pointTwo.y) / (pointOne.x - pointTwo.x);
        }
        return new Factors(a, b, particular);
    }
}