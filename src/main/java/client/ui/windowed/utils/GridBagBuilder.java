package client.ui.windowed.utils;

import java.awt.*;

public class GridBagBuilder {
    private GridBagConstraints constraints = new GridBagConstraints();
    private boolean built = false;

    public GridBagBuilder() {
    }

    public GridBagBuilder(GridBagConstraints constraints) {
        this.constraints = constraints;
    }

    public GridBagBuilder x(int x) {
        constraints.gridx = x;
        return this;
    }

    public GridBagBuilder y(int y) {
        constraints.gridy = y;
        return this;
    }

    public GridBagBuilder colSpan(int span) {
        constraints.gridwidth = span;
        return this;
    }

    public GridBagBuilder rowSpan(int span) {
        constraints.gridheight = span;
        return this;
    }

    public GridBagBuilder weightX(double weightX) {
        constraints.weightx = weightX;
        return this;
    }

    public GridBagBuilder weightY(double weightY) {
        constraints.weighty = weightY;
        return this;
    }

    public GridBagBuilder fill(int fill) {
        constraints.fill = fill;
        return this;
    }

    public GridBagBuilder anchor(int anchor) {
        constraints.anchor = anchor;
        return this;
    }

    public GridBagBuilder insets(int top, int left, int bottom, int right) {
        constraints.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GridBagBuilder ipad(int ipadx, int ipady) {
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        return this;
    }

    public GridBagConstraints build() {
        if (!built) {
            built = true;

            return constraints;
        } else {
            throw new IllegalStateException("Cannot build constraints twice");
        }
    }
}
