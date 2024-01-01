import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}

import java.io.{BufferedWriter, FileWriter}

class CsvSinkFunction(csvFilePath: String) extends RichSinkFunction[Record] {
  override def invoke(record: Record): Unit = {
    val writer = new FileWriter(csvFilePath, true)

    try {
      // Writing records
      writer.write(s"${record.price},${record.ask},${record.bid},${record.date}\n")
    } finally {
      writer.close()
    }
  }
}
