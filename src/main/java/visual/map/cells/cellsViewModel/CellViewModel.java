package visual.map.cells.cellsViewModel;

import javafx.beans.property.SimpleObjectProperty;
import logic.objects.AbstractPositionedObject;

public class CellViewModel {
    private final SimpleObjectProperty<AbstractPositionedObject> out;
    private final SimpleObjectProperty<AbstractPositionedObject> positionedObjectObjectProperty;

    public CellViewModel() {
        positionedObjectObjectProperty = new SimpleObjectProperty<>();
        out = new SimpleObjectProperty<>();
    }

    private void initModel() {
        positionedObjectObjectProperty.addListener((observable, oldValue, newValue) -> out.set(newValue));
    }

    public SimpleObjectProperty<AbstractPositionedObject> positionedObjectObjectPropertyProperty() {
        return positionedObjectObjectProperty;
    }

    public SimpleObjectProperty<AbstractPositionedObject> outProperty() {
        return out;
    }

    public AbstractPositionedObject getPositionedObjectObjectProperty() {
        return positionedObjectObjectProperty.get();
    }

    public void setPositionedObjectObjectProperty(AbstractPositionedObject positionedObjectObjectProperty) {
        this.positionedObjectObjectProperty.set(positionedObjectObjectProperty);
    }

}

