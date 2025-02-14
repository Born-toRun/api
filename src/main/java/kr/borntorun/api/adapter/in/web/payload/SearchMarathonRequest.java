package kr.borntorun.api.adapter.in.web.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class SearchMarathonRequest {
  List<String> locations;
  List<String> courses;
}
