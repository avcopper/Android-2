<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    tools:context=".CitiesFragment">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="5dp">

        <ImageView
            android:id="@+id/back"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="start"
            android:layout_marginStart="15dp"
            android:layout_marginEnd="15dp"
            android:paddingTop="15dp"
            android:paddingBottom="8dp"
            android:src="@drawable/back" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:textColor="@color/colorMain"
            android:layout_marginTop="10dp"
            android:layout_marginStart="5dp"
            android:textSize="20sp"
            android:text="@string/cities"/>

        <TextView
            android:id="@+id/add_city_start"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="end"
            android:text="@string/add"
            android:textColor="@color/colorMain"
            android:paddingStart="15dp"
            android:paddingEnd="15dp"
            android:textSize="35sp" />
    </LinearLayout>

<!--    <ScrollView-->
<!--        android:layout_width="match_parent"-->
<!--        android:layout_height="wrap_content">-->

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recycler_view_city"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" >

            <LinearLayout
                android:id="@+id/cities"