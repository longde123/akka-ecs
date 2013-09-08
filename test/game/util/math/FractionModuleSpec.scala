package game.util.math

import org.specs2.mutable.Specification

class FractionModuleSpec
    extends FractionModule
    with Specification {

  "gcd should return the greatest common denominator" in {
    gcd( 8, 28 ) === 4
    gcd( 3, 7 ) === 1
  }

  "Int :/ Int should convert to Fraction( Int , Int )" in {
    ( 3 :/ 15 ) === Fraction( 3, 15 )
  }

  "(a :/ b :/ c) should convert to Fraction( a, b*c )" in {
    ( 3 :/ 15 :/ 12 ) === Fraction( 3, 15 * 12 )
  }

  "Fraction.toDouble" should {
    "convert a Fraction(a, b) to a Double" in {
      ( 3 :/ 15 ).toDouble === 0.2
    }
    "convert ( (a / b) / c ) to a Double" in {
      ( 15 :/ 3 :/ 2 ).toDouble === 2.5
    }
  }

  "(3 :/ 4) * (4 :/ 3) should return (12 / 12)" in {
    ( 3 :/ 4 ) * ( 4 :/ 3 ) === 12 :/ 12
  }

  "(3 :/ 4) + (4 :/ 3) should return (25 / 12)" in {
    ( 3 :/ 4 ) + ( 4 :/ 3 ) === 25 :/ 12
  }

  "Comparison operators" should {
    "compare the represented value, not the numerator/denominator" in {
      ( 3 :/ 4 ) == ( 6 :/ 8 ) must beTrue
      ( 3 :/ 4 ) >= ( 6 :/ 8 ) must beTrue
      ( 7 :/ 4 ) > ( 6 :/ 8 ) must beTrue
      ( 1 :/ 2 ) <= ( 6 :/ 8 ) must beTrue
      ( 1 :/ 2 ) < ( 6 :/ 8 ) must beTrue
      ( 1 :/ 2 ) != ( 6 :/ 8 ) must beTrue
    }
  }

  "Fraction.reduce" should {
    "turn (12 :/ 12) into 1 / 1" in {
      ( 12 :/ 12 ).reduce === 1 :/ 1
    }
    "turn (8 :/ 28) into (2 / 7)" in {
      ( 8 :/ 28 ).reduce === 2 :/ 7
    }
  }

}