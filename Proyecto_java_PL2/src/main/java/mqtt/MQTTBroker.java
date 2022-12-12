package mqtt;

public class MQTTBroker 
{		
	private static int qos = 2;
	private static String broker = "tcp://79.116.51.30:1883";
	private static String clientId = "mosquito";
		
	public MQTTBroker()
	{
	}

	public static int getQos() {
		return qos;
	}

	public static String getBroker() {
		return broker;
	}

	public static String getClientId() {
		return clientId;
	}			
}


