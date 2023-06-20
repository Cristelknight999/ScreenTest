package de.cristelknight.util;

public class Vec2i {
    public static final Vec2i ZERO = new Vec2i(0, 0);
    public static final Vec2i MAX = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
    public static final Vec2i MIN = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
    public final int x;
    public final int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i times(int f) {
        return new Vec2i(this.x * f, this.y * f);
    }

    public Vec2i divide(int f) {
        return new Vec2i(this.x / f, this.y / f);
    }

    public float dot(Vec2i vec2) {
        return this.x * vec2.x + this.y * vec2.y;
    }

    public Vec2i plus(Vec2i vec2) {
        return new Vec2i(this.x + vec2.x, this.y + vec2.y);
    }

    public Vec2i plus(int f) {
        return new Vec2i(this.x + f, this.y + f);
    }

    public Vec2i minus(Vec2i vec2) {
        return new Vec2i(this.x - vec2.x, this.y - vec2.y);
    }

    public Vec2i minus(int f) {
        return new Vec2i(this.x - f, this.y - f);
    }


    public boolean equals(Vec2i vec2) {
        return this.x == vec2.x && this.y == vec2.y;
    }

    public Vec2i normalized() {
        float f = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        return f < 1.0E-4F ? ZERO : new Vec2i((int) (this.x / f), (int) (this.y / f));
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public float distanceToSqr(Vec2i vec2) {
        float f = vec2.x - this.x;
        float g = vec2.y - this.y;
        return f * f + g * g;
    }

    public Vec2i negated() {
        return new Vec2i(-this.x, -this.y);
    }
}
