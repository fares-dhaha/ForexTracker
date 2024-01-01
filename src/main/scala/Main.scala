import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.common.eventtime.WatermarkStrategy

object Main {
  def main(args: Array[String]): Unit = {

    // Set up the streaming execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Set up the AlphaVantageAPI source
    val ExchangeRate = env.addSource(new ExchangeRateSource)

    // Define a fixed-size sliding window of 10 seconds with a slide interval of 5 seconds
    //val windowSize = Time.seconds(10)
    //val slideInterval = Time.seconds(5)

    // Apply a sliding window to the exchange rate stream
    //val exchangeRateWindowed = xrpExchangeRate
    //  .map(rate => rate * 2)
    //  .timeWindowAll(windowSize, slideInterval)
    //  .reduce((rate1, rate2) => rate1 + rate2)

    // Print the aggregated windowed data to the console
    ExchangeRate.addSink(new CsvSinkFunction("data.csv"))

    // Execute the streaming job
    env.execute("USD/JPY Exchange Rate Streaming")
  }
}