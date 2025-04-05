# coding: utf-8

import argparse

propertieName = 'version'

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('version', help="New version", type=str)
    args = parser.parse_args()

    with open('./gradle.properties', 'r+') as file:
        old_text = ""
        new_text = ""
        new_version: str = args.version
        new_version = new_version.replace('"', '')
        new_version = new_version[1:]
        old_version: str = ""

        # Bad
        for line in file.readlines():
            start = line.find(propertieName)

            if start < 0:
                old_text += line
                continue

            old_version = line[start + 12:-1]
            print(f'Old version: {old_version}, New version: {new_version}')
            old_text += line

        new_text = old_text.replace(old_version, new_version)

        file.seek(0)
        file.truncate(0)
        file.write(new_text)
        file.close()