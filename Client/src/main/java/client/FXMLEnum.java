package client;

public enum FXMLEnum {
    LOGIN {
        @Override
        public String getFxmlFile() {
            return "OperatorFXML.fxml";
        }

        @Override
        public String getTitle() {
            return "Login";
        }
    },

    MAIN {
        @Override
        public String getFxmlFile() {
            return "MainFXML.fxml";
        }

        @Override
        public String getTitle() {
            return "Main";
        }
    };

    public abstract String getFxmlFile();

    public abstract String getTitle();

    public String getStringFromResourceBundle(String key) {
        return java.util.ResourceBundle.getBundle("Bundle").getString(key);
    }
}
