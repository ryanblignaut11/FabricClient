package theSilverEcho.tweaks.cosmetic;

public class b {
	public float a;

	public float b;

	public float c;

	public b() {}

	public b(float paramFloat1, float paramFloat2, float paramFloat3) {
		this.a = paramFloat1;
		this.b = paramFloat2;
		this.c = paramFloat3;
	}

	public b a(float paramFloat1, float paramFloat2, float paramFloat3) {
		this.a = paramFloat1;
		this.b = paramFloat2;
		this.c = paramFloat3;
		return this;
	}

	public b a(b paramb) {
		float f = (float)Math.sqrt(a());
		if (paramb == null)
			return new b(this.a / f, this.b / f, this.c / f);
		return paramb.a(this.a / f, this.b / f, this.c / f);
	}

	public float a() {
		return this.a * this.a + this.b * this.b + this.c * this.c;
	}

	public static b a(b paramb1, b paramb2, b paramb3) {
		if (paramb3 == null)
			return new b(paramb1.a + paramb2.a, paramb1.b + paramb2.b, paramb1.c + paramb2.c);
		paramb3.a(paramb1.a + paramb2.a, paramb1.b + paramb2.b, paramb1.c + paramb2.c);
		return paramb3;
	}
}
