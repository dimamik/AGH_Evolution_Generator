package visual.cellsView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import logic.objects.AbstractPositionedObject;

public class CellViewModel {
    private final SimpleObjectProperty<CellData> out;
    private final SimpleObjectProperty<AbstractPositionedObject> positionedObjectObjectProperty;

    public CellViewModel() {
        positionedObjectObjectProperty = new SimpleObjectProperty<>();
        out = new SimpleObjectProperty<>();
    }

    private void initModel() {
        positionedObjectObjectProperty.addListener(new ChangeListener<>() {

            @Override
            public void changed(ObservableValue<? extends AbstractPositionedObject> observable, AbstractPositionedObject oldValue, AbstractPositionedObject newValue) {
                out.set(new CellData(newValue));
            }
        });
    }

    public AbstractPositionedObject getPositionedObjectObjectProperty() {
        return positionedObjectObjectProperty.get();
    }

    public void setPositionedObjectObjectProperty(AbstractPositionedObject positionedObjectObjectProperty) {
        this.positionedObjectObjectProperty.set(positionedObjectObjectProperty);
    }

    public SimpleObjectProperty<AbstractPositionedObject> positionedObjectObjectPropertyProperty() {
        return positionedObjectObjectProperty;
    }

    public SimpleObjectProperty<CellData> outProperty() {
        return out;
    }

}

