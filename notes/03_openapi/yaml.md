## [YAML Ain't Markup Language](https://ko.wikipedia.org/wiki/YAML)

#### 노드
```yaml
foo:
  bar:
  baz:
    qux:
```
```json
// JSON equivalent
{
  "foo": {
  "bar": {
    "baz": {
    "qux": null
    }
  }
  }
}
```

#### 리스트
```yml
- foo
- bar
```
```yml
- foo
  bar
```
```yml
[foo, bar]
```
```json
// JSON equivalent
[
  "foo",
  "bar"
]
```

#### 해시
```yaml
foo: bar
baz: qux
```
```yaml
{foo: bar, baz: qux}
```
```json
// JSON equivalent
{
  "foo": "bar",
  "baz": "qux"
}
```

#### 줄바꿈 유지
```yaml
foo: |
  #!/usr/bin/env bash
  FOO=BAR
```

#### 줄바꿈 제거
```yaml
foo: <<
  foo
  bar
```

#### List of Hash
```yaml
- foo: bar
- baz: qux
```
```json
// JSON equivalent
[
  {
    "foo": "bar"
  },
  {
    "baz": "qux"
  }
]
```
