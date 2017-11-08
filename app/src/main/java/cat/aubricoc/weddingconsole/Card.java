package cat.aubricoc.weddingconsole;

import java.io.Serializable;

public interface Card extends Serializable {

    String getTitle();

    String getSubtitle();

    String getText();
}
