# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]
### Changed

## [0.1.5] - 2018-03-01
### Changed
- MapDB version bumped to 3.0.6.
### Fixed
- No longer creates bogus subdirectories when given a non-existent
  database filename while in read-only mode.

## [0.1.4] - 2018-03-01
### Changed
- `put!` is now `assoc!` (alias remains)
- `remove!` is now `dissoc!` (alias remains)
- More examples in README

## [0.1.3] - 2017-03-23
### Changed
- `update!` now behaves more like `clojure.core/update` (NB this is a
  minorly breaking change to the API!)

## [0.1.2] - 2017-02-14
### Changed
- Added additional `mapdb` options
### Fixed
- Corruption bug on 32-bit JVMs

[Unreleased]: https://github.com/your-name/spicerack/compare/0.1.2...HEAD
[0.1.2]: https://github.com/your-name/spicerack/compare/0.1.1...0.1.2
