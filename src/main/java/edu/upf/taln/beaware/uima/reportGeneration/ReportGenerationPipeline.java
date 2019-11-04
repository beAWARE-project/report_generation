package edu.upf.taln.beaware.uima.reportGeneration;


import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import java.util.Optional;
import java.util.logging.Logger;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.jcas.JCas;

import edu.upf.taln.beaware.kafka.GenerationKafkaConsumer;
import edu.upf.taln.beaware.kafka.GenerationKafkaReader;


/**
 * This pipeline generates dummy reports upon request
 */
public class ReportGenerationPipeline {

	public static void main(String[] args) throws Exception {

		Logger logger = Logger.getLogger(ReportGenerationPipeline.class.toString());

		String kafkaBrokers = System.getenv("SECRET_MH_BROKERS");
		String kafkaApiKey = System.getenv("SECRET_MH_API_KEY");
		String groupId = Optional.ofNullable(System.getenv("KAFKA_GROUPID")).orElse("report-generator");

		// setup components
		CollectionReaderDescription reader = createReaderDescription(GenerationKafkaReader.class,
				GenerationKafkaReader.PARAM_KAFKATOPIC,"TOP030_REPORT_REQUESTED,TOP033_SUMMARY_REQUESTED",
				GenerationKafkaReader.PARAM_KAFKABROKERS, kafkaBrokers,
				GenerationKafkaReader.PARAM_KAFKASEEKTOEND, true,
				GenerationKafkaReader.PARAM_KAFKAKEY, kafkaApiKey,
				GenerationKafkaReader.PARAM_GROUPID, groupId
				);
		AnalysisEngineDescription writer = createEngineDescription(GenerationKafkaConsumer.class,
				//GenerationKafkaConsumer.PARAM_KAFKATOPIC,"TOP040_TEXT_REPORT_GENERATED",
				GenerationKafkaConsumer.PARAM_KAFKABROKERS, kafkaBrokers,
				GenerationKafkaConsumer.PARAM_KAFKAKEY, kafkaApiKey,
				GenerationKafkaConsumer.PARAM_GROUPID, groupId
				);

		// configure pipeline
		JCasIterable pipeline = new JCasIterable(reader, writer);

		// Run and show results in console
		logger.info("starting pipeline");
		for (JCas jcas : pipeline) {
			//DocumentMetaData meta = selectSingle(jcas, DocumentMetaData.class);
			//logger.info(jcas.getDocumentText());
		}
	}


}
