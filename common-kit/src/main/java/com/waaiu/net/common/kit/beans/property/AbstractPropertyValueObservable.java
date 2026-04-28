/*
 * wanet
 * Copyright (C) 2021 - present   (
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.waaiu.net.common.kit.beans.property;

/**
 * Abstract base implementation of {@link PropertyValueObservable} with listener
 * management.
 *
 * @param < the property value
 * @author
 * @date 2024-04-17
 * @see IntegerProperty
 * @see LongProperty
 * @see StringProperty
 * @see BooleanProperty
 * @see ObjectProperty
 */
abstract class AbstractPropertyValueObservable<T> implements PropertyValueObservable<T> {
    protected boolean valid = true;
    ChangeHelperList<T> helperList;

    @Override
    public void addListener(PropertyChangeListener<? super T> listener) {
        if (this.helperList == null) {
            this.helperList = new ChangeHelperList<>();
        }

        this.helperList.addListener(this, listener);
    }

    @Override
    public void removeListener(PropertyChangeListener<? super T> listener) {
        if (this.helperList != null) {
            this.helperList.removeListener(listener);
        }
    }

    /**
     * Mark the property as invalid and fire change events to all registered
     * listeners.
     */
    protected void markInvalid() {
        if (this.valid) {
            this.valid = false;
            this.fireValueChangedEvent();
        }
    }

    private void fireValueChangedEvent() {
        if (this.helperList != null) {
            this.helperList.fireValueChangedEvent();
        }
    }
}
