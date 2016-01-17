package com.explore.listeners;

/**
 * Interface definition for a callback to be invoked when a list item is clicked.
 *
 * Author vgrec, on 17.01.16.
 */
public interface OnItemClickListener<T> {
    void onItemClick(T item);
}
