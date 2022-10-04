package dev.appkr.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.*;
import java.util.List;

public class App {

  public static void main(String[] args) throws IOException {
    final String templates = """
        Items {{{items}}} !! {{! This is a comment, and it won't be rendered }}
        
        {{#items}}
          {{{.}}} {{! 2 indent }}
        {{/items}}
        
        {{#items}}
        Name: {{name}}
        Price: {{price}}
          {{#features}}
          Feature: {{description}}
          {{/features}}
        Stock: {{#soldOut}}sold out{{/soldOut}}{{^soldOut}}in stock{{/soldOut}}
        {{/items}}
        """;

    final MustacheFactory mf = new DefaultMustacheFactory();
    final Mustache mustache = mf.compile(new StringReader(templates), "test-template");
    mustache.execute(new PrintWriter(System.out), new Example()).flush();
//    mustache.execute(new FileWriter(System.getProperty("user.home") + "/mustache.txt"), new Example()).flush();
  }

  static class Example {
    List<Item> items() {
      return List.of(
          new Item("Item 1", "$19.99", List.of(new Feature("New!"), new Feature("Awesome!")), true),
          new Item("Item 2", "$29.99", List.of(new Feature("Old."), new Feature("Ugly.")), false)
      );
    }
  }

  static record Item(String name, String price, List<Feature> features, boolean soldOut) {}

  static record Feature(String description) {}
}
