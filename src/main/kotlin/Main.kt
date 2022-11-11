import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions

fun main(args: Array<String>) {

    val logger = java.util.logging.Logger.getGlobal()

    val consumerConfig = mutableMapOf(
        CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to "localhost:9093",
        ConsumerConfig.GROUP_ID_CONFIG to "consumerGroup1",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.qualifiedName,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ByteArrayDeserializer::class.qualifiedName,
    )


    val receiver: KafkaReceiver<String, ByteArray> = KafkaReceiver.create(
        ReceiverOptions.create<String, ByteArray?>(consumerConfig.toMap() as Map<String, *>)
            .subscription(setOf("topic"))
    )

    receiver.receive()
        .doOnSubscribe {
            receiver.doOnConsumer {
                logger.info("doOnConsumer called")
            }.subscribe()
        }.subscribe {
            println("consumed message")
        }
}