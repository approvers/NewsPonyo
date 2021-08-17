import newsPonyo._
import org.scalatest.FunSuite

class AddNewsTest extends FunSuite {
  test("ValidateTest error") {
    assert(
        AddNews.variation(null, Array.fill(1)("TEST")) === Left(
            Faild(null, "引数が少ないです。タイトルも入力してください")
        )
    )
    assert(
        AddNews
          .variation(null, Array.fill(10)("TEST")) === Left(
            Faild(null, "引数が多いです。余計なスペースなどがないか確認してください\n※タイトルには半角スペースが使えません")
        )
    )
  }

  test("ValidateTest Success") {
    assert(AddNews.variation(null, Array.fill(2)("TEST")) === Right({}))
  }
}
