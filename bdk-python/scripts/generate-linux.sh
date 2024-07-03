#!/usr/bin/env bash

set -euo pipefail
${PYBIN}/python --version
${PYBIN}/pip install -r requirements.txt

cd ../bdk-ffi/

rustup default 1.77.1

echo "Generating native binaries..."
cargo build --profile release-smaller

echo "Generating bdk.py..."
cargo run --bin uniffi-bindgen generate --library ./target/release-smaller/libbdkffi.so --language python --out-dir ../bdk-python/src/bdkpython/ --no-format

echo "Copying linux libbdkffi.so..."
cp ./target/release-smaller/libbdkffi.so ../bdk-python/src/bdkpython/libbdkffi.so

echo "All done!"

# cd ../bdk-python

# echo "Building wheel"

# python3 setup.py --verbose bdist_wheel

# echo "Installing BDK Python"

# pip3 install ./dist/bdkpython-1.0.0b2.dev0-cp310-cp310-linux_x86_64.whl --force-reinstall