import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

class MyModel {
    public static final String Temperature = "Temperature";
    public static final String Humidity = "Humidity";
    public static final String UVIndex = "uvIndex";

    private List<WeatherData> weatherData = new ArrayList<WeatherData>();
    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    public class WeatherData {

        private Float temp;

        private Float humidity;
        
        private Float uvIndex;

        public WeatherData(Float temp, Float humidity, Float uvIndex) {
            this.temp = temp;
            this.humidity = humidity;
            this.uvIndex = uvIndex;
            System.out.println("Current Weather: ");
            System.out.println("Temperature: " + temp);
            System.out.println("Humidity: " + humidity);
            System.out.println("uvIndex: " + uvIndex);
        }

        public Float getTemp() {

            return temp;
        }

        public void setTemp(Float temp) {
            notifyListeners(
                    this,
                    Temperature,
                    this.temp,
                    this.temp = temp);

        }

        public Float getHumidity() {
            return humidity;
        }

        public void setHumidity(Float humidity) {
            notifyListeners(
                    this,
                    Humidity,
                    this.humidity,
                    this.humidity = humidity);
        }
        
        public Float getUvIndex() {

            return uvIndex;
        }

        public void setUvIndex(Float uvIndex) {
            notifyListeners(
                    this,
                    UVIndex,
                    this.uvIndex,
                    this.uvIndex = uvIndex);

        }
    }

    public List<WeatherData> getWeatherData() {
        return weatherData;
    }

    public MyModel() {
        // just for testing we hard-code the persons here:
        weatherData.add(new WeatherData(35f, 30f, 0.5f));
    }

    private void notifyListeners(Object object, String property, Float oldValue, Float newValue) {
        for (PropertyChangeListener data : listener) {
            data.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

}

class MyObserver1 implements PropertyChangeListener {
    public MyObserver1(MyModel model) {
        model.addChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        System.out.println("Weather Station 1: " + event.getPropertyName() + ": " + event.getNewValue());
    }
}

class MyObserver2 implements PropertyChangeListener {
    public MyObserver2(MyModel model) {
        model.addChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        System.out.println("Weather Station 2: " + event.getPropertyName() + ": " + event.getNewValue());
    }
}

class Main {

    public static void main(String[] args) {
        MyModel model = new MyModel();
        MyObserver1 observer = new MyObserver1(model);
        MyObserver2 observer2 = new MyObserver2(model);

        System.out.println("\nUpdated Weather:");
        // we change the last name of the person, observer will get notified
        for (MyModel.WeatherData weatherData : model.getWeatherData()) {
            weatherData.setHumidity((weatherData.getHumidity() + 1f));
        }
        // we change the name of the person, observer will get notified
        for (MyModel.WeatherData weatherData : model.getWeatherData()) {
            weatherData.setTemp((weatherData.getTemp() + 1f));
        }
        // we change the name of the person, observer will get notified
        for (MyModel.WeatherData weatherData : model.getWeatherData()) {
            weatherData.setUvIndex((weatherData.getUvIndex() + 0.1f));
        }
    }
}