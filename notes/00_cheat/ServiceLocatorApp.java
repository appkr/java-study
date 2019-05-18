import java.io.IOException;

public class ServiceLocatorApp {

    private ServiceLocator serviceLocator;

    public static void main(String[] args) throws IOException {
        ServiceLocatorApp app = new ServiceLocatorApp();
        app.setServiceLocator(new ServiceLocator());
    }

    private void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    static class ServiceLocator {
//        public DeliveryRepository deliveryRepository() {
//            return new PrimeApiClient(
//                new DeliveryHandler(),
//                new ExternalDeliveryMapper()
//            );
//        }
//
//        public DeliveryService deliveryService() {
//            return new DeliveryService(
//                deliveryRepository(),
//                new DeliveryMapper()
//            );
//        }
//
//        public DeliveryController deliveryController() {
//            return new DeliveryController(deliveryService());
//        }
    }
}
