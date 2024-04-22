import 'package:flutter/material.dart';

class Product {
  final int id;
  final String title, description;
  final List<String> images;
  final List<Color> colors;
  final double rating, price;
  final bool isFavourite, isPopular;

  Product({
    required this.id,
    required this.images,
    required this.colors,
    this.rating = 0.0,
    this.isFavourite = false,
    this.isPopular = false,
    required this.title,
    required this.price,
    required this.description,
  });
}

// Our demo Products

List<Product> demoProducts = [
  Product(
    id: 1,
    images: [
      "assets/images/shoes/Pro_A6T015_1.jpeg",
      "assets/images/shoes/Pro_A6T015_2.jpeg",
      "assets/images/shoes/Pro_A6T015_3.jpeg",
      "assets/images/shoes/Pro_A6T015_5.jpeg",
    ],
    colors: [
      const Color(0xFFF6625E),
      const Color(0xFF836DB8),
      const Color(0xFFDECB9C),
      Colors.white,
    ],
    title: "Pro A6T015™",
    price: 25.99,
    description: description,
    rating: 4.8,
    isFavourite: true,
    isPopular: true,
  ),
  Product(
    id: 2,
    images: [
      "assets/images/shoes/Pro_AV00174_1.jpeg",
      "assets/images/shoes/Pro_AV00174_2.jpeg",
      "assets/images/shoes/Pro_AV00174_3.jpeg",
      "assets/images/shoes/Pro_AV00174_4.jpeg",
    ],
    colors: [
      const Color(0xFFF6625E),
      const Color(0xFF836DB8),
      const Color(0xFFDECB9C),
      Colors.white,
    ],
    title: "Pro AV00174",
    price: 20.5,
    description: description,
    rating: 4.1,
    isPopular: true,
  ),
  Product(
    id: 3,
    images: [
      "assets/images/shoes/Pro_AV00180_1.jpeg",
      "assets/images/shoes/Pro_AV00180_2.jpeg",
      "assets/images/shoes/Pro_AV00180_3.jpeg",
      "assets/images/shoes/Pro_AV00180_4.jpeg",
    ],
    colors: [
      const Color(0xFFF6625E),
      const Color(0xFF836DB8),
      const Color(0xFFDECB9C),
      Colors.white,
    ],
    title: "Pro AV00180",
    price: 36.55,
    description: description,
    rating: 4.1,
    isFavourite: true,
    isPopular: true,
  ),
  Product(
    id: 4,
    images: [
      "assets/images/shoes/Pro_AV00204_1.jpeg",
      "assets/images/shoes/Pro_AV00204_2.jpeg",
      "assets/images/shoes/Pro_AV00204_3.jpeg",
      "assets/images/shoes/Pro_AV00204_4.jpeg",
    ],
    colors: [
      const Color(0xFFF6625E),
      const Color(0xFF836DB8),
      const Color(0xFFDECB9C),
      Colors.white,
    ],
    title: "Pro AV00204",
    price: 20.20,
    description: description,
    rating: 4.1,
    isFavourite: true,
  ),
  Product(
    id: 5,
    images: [
      "assets/images/shoes/Pro_AV00205_1.jpeg",
      "assets/images/shoes/Pro_AV00205_2.jpeg",
      "assets/images/shoes/Pro_AV00205_3.jpeg",
      "assets/images/shoes/Pro_AV00205_4.jpeg",
    ],
    colors: [
      const Color(0xFFF6625E),
      const Color(0xFF836DB8),
      const Color(0xFFDECB9C),
      Colors.white,
    ],
    title: "Pro AV00205",
    price: 24.99,
    description: description,
    rating: 4.8,
    isFavourite: true,
    isPopular: true,
  ),
  Product(
    id: 6,
    images: [
      "assets/images/shoes/Pro_AV00207_1.jpg",
      "assets/images/shoes/Pro_AV00207_2.jpg",
      "assets/images/shoes/Pro_AV00207_3.jpg",
      "assets/images/shoes/Pro_AV00207_4.jpg",
    ],
    colors: [
      const Color(0xFFF6625E),
      const Color(0xFF836DB8),
      const Color(0xFFDECB9C),
      Colors.white,
    ],
    title: "Pro AV00207",
    price: 50.5,
    description: description,
    rating: 4.1,
    isPopular: true,
  ),
];

const String description =
    "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English";
