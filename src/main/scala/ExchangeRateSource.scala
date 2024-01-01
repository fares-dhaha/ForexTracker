import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.json4s._
import org.json4s.native.JsonMethods._

import java.io.{BufferedReader, InputStreamReader}
import java.io.{BufferedWriter, FileWriter, File}
import java.net.{HttpURLConnection, URL}
import scala.util.{Failure, Success, Try}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ExchangeRateSource extends SourceFunction[Record] {
  override def run(ctx: SourceFunction.SourceContext[Record]): Unit = {
    val apiKey = "335de10327msh2ef80e3607501c3p111d77jsn0a1ac38081a0"
    val fromCurrency = "USD"
    val toCurrency = "JPY"
    val function = "CURRENCY_EXCHANGE_RATE"
    val url = s"https://alpha-vantage.p.rapidapi.com/query?from_currency=$fromCurrency&function=$function&to_currency=$toCurrency"

    while (true) {
      Try {
        val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        connection.setRequestProperty("X-RapidAPI-Key", apiKey)
        connection.setRequestProperty("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")

        val responseCode = connection.getResponseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
          val in = new BufferedReader(new InputStreamReader(connection.getInputStream))
          val response = new StringBuilder
          var inputLine: String = null
          while ({ inputLine = in.readLine; inputLine != null }) {
            response.append(inputLine)
          }
          in.close()

          implicit val formats: DefaultFormats.type = DefaultFormats
          val json = parse(response.toString)
          val price = (json \\ "5. Exchange Rate").extract[String].toDouble
          val bid = (json \\ "8. Bid Price").extract[String].toDouble
          val ask = (json \\ "9. Ask Price").extract[String].toDouble
          // Get the current date and time
          val currentDateTime: LocalDateTime = LocalDateTime.now()

          // Define a formatter if you want to customize the output
          val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

          // Format the current date and time using the formatter
          val formattedDateTime: String = currentDateTime.format(formatter)
          println(s"$formattedDateTime: USD/JPY Exchange Rate: $price (Ask Price: $ask / Bid Price: $bid)")
          val record = Record(price, ask, bid, formattedDateTime)

          ctx.collect(record)
        } else if (responseCode == 429) {
          // If we exceed the API limit, wait for a minute and try again
          Thread.sleep(60000)
        } else {
          throw new RuntimeException(s"HTTP GET request failed with error code: $responseCode")
        }
      } match {
        case Success(_) =>
        case Failure(ex) =>
          ex.printStackTrace()
      }

      Thread.sleep(13000) // Stream the data every 13 seconds
    }
  }


  override def cancel(): Unit = {}
}
