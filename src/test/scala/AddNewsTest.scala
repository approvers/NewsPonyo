import newsPonyo.AddNews
import org.scalatest.FunSuite

class AddNewsTest extends FunSuite {
  test("ValidateTest error") {
    assert(AddNews.variation(Array.fill(1)("TEST")) === Left("引数不足"))
    assert(AddNews.variation(Array.fill(10)("TEST")) === Left("引数過度"))
  }

  test("ValidateTest Success") {
    assert(AddNews.variation(Array.fill(2)("TEST")) === Right({}))
  }
}
