package edu.upf.taln.beaware.uima.reportGeneration;


import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;

import java.util.logging.Logger;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import edu.upf.taln.beaware.uima.consumer.BeAwareKafkaReportGenerator;
import edu.upf.taln.beaware.uima.reader.BeAwareKafkaObserver;

/**
 * This pipeline generates dummy reports upon request
 */
public class ReportGenerationPipeline {

	public static void main(String[] args) throws Exception {

		Logger logger = Logger.getLogger(ReportGenerationPipeline.class.toString());

		String mongoDb = "BeAware";
		String mongoUri = System.getenv("SECRET_MONGO_URI");
		String kafkaBrokers = System.getenv("SECRET_MH_BROKERS");
		String kafkaApiKey = System.getenv("SECRET_MH_API_KEY");

		//Unirest.get("http://glicom.upf.edu/beaware")
		//.queryString("log", "environment")
		//.queryString("SECRET_MONGO_URI", mongoUri)
		//.queryString("SECRET_MH_BROKERS", kafkaBrokers)
		//.queryString("SECRET_MH_API_KEY", kafkaApiKey)
		//.asString();

		// setup components
		CollectionReaderDescription reader = createReaderDescription(BeAwareKafkaObserver.class,
				BeAwareKafkaObserver.PARAM_KAFKATOPIC,"TOP030_REPORT_REQUESTED",
				BeAwareKafkaObserver.PARAM_KAFKABROKERS, kafkaBrokers,
				BeAwareKafkaObserver.PARAM_KAFKASEEKTOEND, true,
				BeAwareKafkaObserver.PARAM_KAFKAKEY, kafkaApiKey,
				BeAwareKafkaObserver.PARAM_GROUPID, "report-generator"
				);
		AnalysisEngineDescription writer = createEngineDescription(BeAwareKafkaReportGenerator.class,
				BeAwareKafkaReportGenerator.PARAM_KAFKATOPIC,"TOP040_TEXT_REPORT_GENERATED",
				BeAwareKafkaReportGenerator.PARAM_KAFKABROKERS, kafkaBrokers,
				BeAwareKafkaReportGenerator.PARAM_KAFKAKEY, kafkaApiKey
				);

		// configure pipeline
		JCasIterable pipeline = new JCasIterable(reader, writer);

		// Run and show results in console
		logger.info("starting pipeline");
		//Unirest.get("http://glicom.upf.edu/beaware")
		//.queryString("log", "starting pipeline")
		//.asString();
		for (JCas jcas : pipeline) {
			//DocumentMetaData meta = selectSingle(jcas, DocumentMetaData.class);
			//Unirest.get("http://glicom.upf.edu/beaware")
			//	.queryString("log", "processing")
			//	.queryString("text", jcas.getDocumentText())
			//	.queryString("id", meta.getDocumentId())
			//	.asString();
			//logger.info(jcas.getDocumentText());
		}
	}


}
